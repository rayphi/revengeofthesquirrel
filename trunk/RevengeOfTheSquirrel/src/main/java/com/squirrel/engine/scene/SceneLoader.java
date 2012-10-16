package com.squirrel.engine.scene;

import static com.squirrel.engine.scene.impl.SceneArchiveConstants.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import org.apache.log4j.Logger;

import com.squirrel.engine.asset.Asset;
import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.layer.Layer;
import com.squirrel.engine.scene.impl.SceneImpl;
import com.squirrel.engine.utils.ApplicationUtils;

/**
 * Dieser Loader lädt ein Archiv mit Scene informationen und
 * stellt über die Methode {@link #loadScene(String)} die Möglichkeit zur Verfügung
 * stellt eine bestimmte {@link Scene} aus dem Archiv zu laden.
 * 
 * @author Shane
 *
 */
public class SceneLoader {
	
	private static final Logger logger = Logger.getLogger(SceneLoader.class);

	/**
	 * Der Pfad zu dem Archiv mit den Scene Informationen
	 */
	private String pathToArchive;
	/**
	 * Das Archiv wird während des Ladevorganges hier gespeichert
	 */
	private ZipFile zipFile;
	
	/**
	 * Initalisiert den {@link SceneLoader}
	 * 
	 * @param pathToArchive
	 */
	public SceneLoader(String pathToArchive) {
		this.pathToArchive = pathToArchive;
	}
	
	/**
	 * Lädt die {@link Scene} mit dem übergebenen Namen aus
	 * dem Archiv, stellt dieses her und gibt es zurück.
	 * Zu diesem Zweck muss es in dem Archiv die Datei 'name-der-scene'.xml geben
	 * 
	 * @param sceneName
	 * @return {@link Scene} oder null
	 */
	public Scene loadScene(String sceneName) {
		Scene sc = null;
		
		try {
			
			openSceneArchive();
			
			sc = loadSceneFromArchive(sceneName);
			
		} catch (Exception e) {
			logger.error("Exception caught loading Scene: ", e);
		} finally {
			try {
				zipFile.close();
			} catch (IOException e) {
				logger.warn("Exception caught closing zipFile: ", e);
			}
		}
		
		return sc;
	}

	/**
	 * Lädt eine {@link Scene} aus dem geöffneten Archiv und gibt diese zurück
	 * 
	 * @param sceneName
	 * @return {@link Scene} oder null
	 */
	private Scene loadSceneFromArchive(String sceneName) throws Exception {
		Scene scene = null;
		
		ZipEntry ze = zipFile.getEntry(sceneName);
		
		// EventFactory erzeugen
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = inputFactory.createXMLStreamReader(zipFile.getInputStream(ze));
		
		while (reader.hasNext()) {
			int evt = reader.next();
			
			if (evt == XMLStreamConstants.START_ELEMENT) {
				
				// Hier beginnt die Scene
				if (reader.getLocalName().equalsIgnoreCase(SCENE)) {
					scene = new SceneImpl();
					
					while(reader.hasNext()) {
						evt = reader.next();
						
						// Prüfen ob ein Layer folgt und diesen entsprechend einlesen
						if (evt == XMLStreamConstants.START_ELEMENT) {
							if (reader.getLocalName().equalsIgnoreCase(LAYER)) {
								Layer layer = readLayer(reader);
								if (layer != null)
									scene.addLayer(layer);
							}
						}
						
						// Prüfen, ob bereits alle Layer eingelesen wurden
						if (evt == XMLStreamConstants.END_ELEMENT) {
							if (reader.getLocalName().equalsIgnoreCase(SCENE)) {
								break;
							}
						}
						
					}
					
				}
				
			}
			
			// Prüfen, ob das Dokument zuende ist
			if (evt == XMLStreamConstants.END_DOCUMENT) {
				// Import ist abgeschlossen
				break;
			}
		}
		
		return scene;
	}

	/**
	 * liest Layerinformationen aus der Datei ein und und erzeugt einen entsprechenden
	 * Layer inklusive aller enthaltenen {@link GameObject}s und Assets.
	 * 
	 * @param reader
	 * @return {@link Layer} oder null
	 */
	private Layer readLayer(XMLStreamReader reader) throws Exception {
		Layer layer = null;
		Map<String, Object> layerMap = new HashMap<String, Object>();
		
		while (reader.hasNext()) {
			int evt = reader.next();
			
			if (evt == XMLStreamConstants. START_ELEMENT) {
				
				// type
				if (reader.getLocalName().equalsIgnoreCase(TYPE)) {
					if (reader.next() == XMLStreamConstants.CHARACTERS) {
						if (!reader.isWhiteSpace()) {
							layerMap.put(TYPE, reader.getText());
						}
					}
				}
				
				// name
				if (reader.getLocalName().equalsIgnoreCase(NAME)) {
					if (reader.next() == XMLStreamConstants.CHARACTERS) {
						if (!reader.isWhiteSpace()) {
							layerMap.put(NAME, reader.getText());
						}
					}
				}
				
				// priority
				if (reader.getLocalName().equalsIgnoreCase(PRIORITY)) {
					if (reader.next() == XMLStreamConstants.CHARACTERS) {
						if (!reader.isWhiteSpace()) {
							layerMap.put(PRIORITY, Long.parseLong(reader.getText()));
						}
					}
				}
				
				// content (optional)
				if (reader.getLocalName().equalsIgnoreCase(CONTENT)) {
					List<GameObject> gos = readGameObjects(reader);
					if (gos != null) {
						layerMap.put(CONTENT, gos);
					}
				}
				
				// specifics (optional)
				if (reader.getLocalName().equalsIgnoreCase(MAP)) {
					Map<String, Object> map = readMap(reader);
					if (map != null) {
						layerMap.put(MAP, map);
					}
				}
				
			}
			
			if (evt == XMLStreamConstants.END_ELEMENT) {
				if (reader.getLocalName().equalsIgnoreCase(LAYER)) {
					// Der Layer ist zuende
					break;
				}
			}
		}
		
		// Den Layer erzeugen
		String type = (String) layerMap.get(TYPE);
		if (type != null) {
			Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(type);
			
			Object obj = clazz.newInstance();
			
			if (obj instanceof Layer) {
				layer = (Layer) obj;
				layer.load(layerMap);
			} else {
				logger.error(type + " is no Implementation of com.squirrel.engine.scene.Layer.");
			}
			
		} else {
			logger.error("No type found.");
		}
		
		return null;
	}

	/**
	 * Liest ab der aktuellen Position des Reader Implementierungsspezifische Informationen ein
	 * 
	 * @param reader
	 * @return
	 * @throws Exception 
	 */
	private Map<String, Object> readMap(XMLStreamReader reader) throws Exception {
		Map<String, Object> specifics = new HashMap<String, Object>();
		
		while (reader.hasNext()) {
			int evt = reader.next();
			
			if (evt == XMLStreamConstants.START_ELEMENT) {
				if (reader.getLocalName().equalsIgnoreCase(SPRITESHEET)) {
					String name = reader.getAttributeValue(null, ASSET_ID);
					SpriteAsset[] spriteArr = readSpriteSheet(reader);
					if (spriteArr != null) {
						specifics.put(name, spriteArr);
					}
				} else if (reader.getLocalName().equalsIgnoreCase(ASSETREF)) {
					Map<String, Asset> assetref = readAssetRefs(reader);
					if (assetref != null) {
						specifics.put(ASSETREF, assetref);
					}
				} else if (reader.getLocalName().equalsIgnoreCase(LIST)) {
					List<Object> list = readList(reader);
					if (list != null) {
						specifics.put(LIST, list);
					}
				} else if (reader.getLocalName().equalsIgnoreCase(MAP)) {
					Map<String, Object> map = readMap(reader);
					if (map != null) {
						specifics.put(MAP, map);
					}
				} else {
					if (reader.next() == XMLStreamConstants.CHARACTERS) {
						String property = reader.getLocalName();
						if (!reader.isWhiteSpace()) {
							specifics.put(property, reader.getText());
						}
					}
				}
			}
			
			// Prüfen ob alle specifics eingelesen wurden
			if (evt == XMLStreamConstants.END_ELEMENT) {
				if (reader.getLocalName().equalsIgnoreCase(MAP)) {
					break;
				}
			}
		}
		
		return specifics;
	}

	/**
	 * Liest ein spritesheet ein ab der position des reader und
	 * gibt eine Map
	 * @param reader
	 * @return
	 */
	private SpriteAsset[] readSpriteSheet(XMLStreamReader reader) {
		SpriteAsset[] spriteArr = null;
		
		// TODO spritesheet importieren
		
		return spriteArr;
	}

	/**
	 * Liest alle Assets ab der aktuellen osition des reader ein
	 * erzeugt die entsprechenden Objekte und gibt diese in form
	 * einer {@link Map} zurück
	 * 
	 * @param reader
	 * @return
	 * @throws Exception 
	 */
	private Map<String, Asset> readAssetRefs(XMLStreamReader reader) throws Exception {
		Map<String, Asset> aMap = new HashMap<String, Asset>();
		
		AssetManager am = (AssetManager) ApplicationUtils.getInstance().getBean("assetManager");
		
		while (reader.hasNext()) {
			int evt = reader.next();
			
			if (evt == XMLStreamConstants.START_ELEMENT) {
				if (reader.next() == XMLStreamConstants.CHARACTERS) {
					
					String property = reader.getLocalName();
					String assetIdentifier = reader.getAttributeValue(null, ASSET_ID);
					if (!reader.isWhiteSpace()) {
						
						String fileName = reader.getText();
						ZipEntry assetFile = zipFile.getEntry(fileName);
						if (assetFile != null) {
							aMap.put(property, am.load(assetIdentifier, zipFile.getInputStream(assetFile)));
						} else  {
							throw new Exception("File " + fileName + " does not exist within archive.");
						}
						
					}
					
				}
			}
			
			if (evt == XMLStreamConstants.END_ELEMENT) {
				if (reader.getLocalName().equalsIgnoreCase(ASSETREF)) {
					break;
				}
			}
		}
		
		return aMap;
	}

	/**
	 * List ab der aktuellen Position des Reader eine {@link List} von String ein
	 * 
	 * @param reader
	 * @return
	 * @throws Exception 
	 */
	private List<Object> readList(XMLStreamReader reader) throws Exception {
		List<Object> list = new ArrayList<Object>();
		
		while (reader.hasNext()) {
			int evt = reader.next();
			
			if (evt == XMLStreamConstants.START_ELEMENT) {
				if (reader.getLocalName().equalsIgnoreCase(ASSETREF)) {
					Map<String, Asset> assetref = readAssetRefs(reader);
					if (assetref != null) {
						list.add(assetref);
					}
				} else if (reader.getLocalName().equalsIgnoreCase(MAP)) {
					Map<String, Object> map = readMap(reader);
					if (map != null) {
						list.add(map);
					}
				} else if (reader.getLocalName().equalsIgnoreCase(LIST)) {
					List<Object> l = readList(reader);
					if (l != null) {
						list.add(l);
					}
				} else {
					if (reader.next() == XMLStreamConstants.CHARACTERS) {
						if (!reader.isWhiteSpace()) {
							list.add(reader.getText());
						}
					}
				}
			}
			
			if (evt == XMLStreamConstants.END_ELEMENT) {
				if (reader.getLocalName().equalsIgnoreCase(LIST)) {
					break;
				}
			}
		}
		
		return list;
	}

	/**
	 * Liest ab der aktuellen Position des reader GameObjects ein
	 * 
	 * @param reader
	 * @return
	 * @throws Exception 
	 */
	private List<GameObject> readGameObjects(XMLStreamReader reader) throws Exception {
		List<GameObject> gos = new ArrayList<GameObject>();
		
		while (reader.hasNext()) {
			int evt = reader.next();
			
			if (evt == XMLStreamConstants.START_ELEMENT) {
				if (reader.getLocalName().equalsIgnoreCase(GAMEOBJECT)) {
					GameObject go = readGameObject(reader);
					if (go != null) {
						gos.add(go);
					}
				}
			}
			
			// Prüfen ob alle GameObjects eingelesen wurden
			if (evt == XMLStreamConstants.END_ELEMENT) {
				if (reader.getLocalName().equalsIgnoreCase(CONTENT)) {
					break;
				}
			}
		}
		
		return gos;
	}

	/**
	 * Liest ein einzelnes {@link GameObject} ein
	 * 
	 * @param reader
	 * @return
	 * @throws Exception 
	 */
	private GameObject readGameObject(XMLStreamReader reader) throws Exception {
		GameObject go = null;
		Map<String, Object> goMap = new HashMap<String, Object>();
		
		while (reader.hasNext()) {
			int evt = reader.next();
			
			if (evt == XMLStreamConstants.START_ELEMENT) {
				// type
				if (reader.getLocalName().equalsIgnoreCase(TYPE)) {
					if (!reader.isWhiteSpace()) {
						goMap.put(TYPE, reader.getText());
					}
				}
				
				// name
				if (reader.getLocalName().equalsIgnoreCase(NAME)) {
					if (!reader.isWhiteSpace()) {
						goMap.put(NAME, reader.getText());
					}
				}
				
				// position X
				if (reader.getLocalName().equalsIgnoreCase(POS_X)) {
					if (!reader.isWhiteSpace()) {
						goMap.put(POS_X, Double.parseDouble(reader.getText()));
					} else {
						goMap.put(POS_X, new Double(0));
					}
				}
				
				// position Y
				if (reader.getLocalName().equalsIgnoreCase(POS_Y)) {
					if (!reader.isWhiteSpace()) {
						goMap.put(POS_Y, Double.parseDouble(reader.getText()));
					} else {
						goMap.put(POS_Y, new Double(0));
					}
				}
				
				// specifics (optional)
				if (reader.getLocalName().equalsIgnoreCase(MAP)) {
					Map<String, Object> map = readMap(reader);
					if (map != null) {
						goMap.put(MAP, map);
					}
				}
				
			}
			
			// Prüfen ob das GameObject zuende eingelesen wurde
			if (evt == XMLStreamConstants.END_ELEMENT) {
				if (reader.getLocalName().equalsIgnoreCase(GAMEOBJECT)) {
					break;
				}
			}
		}
		
		// GameObject erzeugen
		String type = (String) goMap.get(TYPE);
		if (type != null) {
			Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(type);
			
			Object obj = clazz.newInstance();
			
			if (obj instanceof Layer) {
				go = (GameObject) obj;
				go.load(goMap);
			} else {
				logger.error(type + " is no Implementation of com.squirrel.engine.gameobject.GameObject.");
			}
			
		} else {
			logger.error("No type found.");
		}
		
		return go;
	}

	/**
	 * Öffnet das Archiv für einen folgenden Ladevorgang
	 * 
	 * @throws Exception
	 */
	private void openSceneArchive() throws Exception {
		File file = new File(pathToArchive);
		
		// Prüfen, ob es die Datei gibt
		if (file.exists()) {
			throw new FileNotFoundException(pathToArchive + " not found.");
		}
		
		// Prüfen ob es sich um eine Datei handelt
		if (!file.isFile()) {
			throw new Exception(pathToArchive + " is not a file.");
		}
		
		// Als Zip Archiv öffnen
		zipFile = new ZipFile(file);
		
	}
}
