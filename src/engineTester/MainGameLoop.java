package engineTester;

import java.util.ArrayList;import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		//****************************TERRAIN TEXTURE STUFF***********************************
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		//************************************************************************************
		
		//****************************TEXTURE CREATION STUFF**********************************
		TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
		TexturedModel lowPolyTree = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader), new ModelTexture(loader.loadTexture("lowPolyTree")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
		TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("flower")));
		TexturedModel dragon = new TexturedModel(OBJLoader.loadObjModel("dragon", loader), new ModelTexture(loader.loadTexture("dragonTexture")));
		TexturedModel playerModel = new TexturedModel(OBJLoader.loadObjModel("person", loader), new ModelTexture(loader.loadTexture("playerTexture")));
		
		Player player = new Player(playerModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);

		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setUseFakeLighting(true);
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);
		
		//************************************************************************************
		
		//****************************TEXTURE RENDERING STUFF*********************************
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i = 0; i < 1000; i++){
			entities.add(new Entity(tree, new Vector3f(random.nextFloat() * 1600,0,random.nextFloat() * 800), 0, 0, 0, 3));
			entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 1600,0,random.nextFloat() * 800), 0, 0, 0, 1));
			entities.add(new Entity(flower, new Vector3f(random.nextFloat() * 1600,0,random.nextFloat() * 800), 0, 0, 0, 1));
			entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 1600,0,random.nextFloat() * 800), 0, 0, 0, 0.6f));
		}
		
		for(int i = 0; i < 500; i++) {
			entities.add(new Entity(lowPolyTree, new Vector3f(random.nextFloat() * 1600,0,random.nextFloat() * 800), 0, 0, 0, 1));
		}
		
		entities.add(new Entity(dragon, new Vector3f(10, 0, 10), 0, 0, 0, 1));
		
		dragon.getTexture().setReflectivity(0.6f);
		tree.getTexture().setReflectivity(0.1f);
		grass.getTexture().setReflectivity(0.1f);
		fern.getTexture().setReflectivity(0.1f);
		
		//************************************************************************************
		
		Light light = new Light(new Vector3f(200, 2000, 200),new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(1, 0, loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(0, 0, loader, texturePack, blendMap);
		
		Camera camera = new Camera(player);	
		MasterRenderer renderer = new MasterRenderer();
		
		
		while(!Display.isCloseRequested()){
			camera.move();
			player.move();
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
