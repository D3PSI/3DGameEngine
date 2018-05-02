package engineTester;

import java.util.ArrayList;import java.util.List;
import java.util.Random;

import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
		TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("flower")));
		TexturedModel dragon = new TexturedModel(OBJLoader.loadObjModel("dragon", loader), new ModelTexture(loader.loadTexture("dragonTexture")));
		
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setUseFakeLighting(true);
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i = 0; i < 1000; i++){
			entities.add(new Entity(tree, new Vector3f(random.nextFloat() * 1600,0,random.nextFloat() * 800), 0, 0, 0, 3));
			entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 1600,0,random.nextFloat() * 800), 0, 0, 0, 1));
			entities.add(new Entity(flower, new Vector3f(random.nextFloat() * 1600,0,random.nextFloat() * 800), 0, 0, 0, 1));
			entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 1600,0,random.nextFloat() * 800), 0, 0, 0, 0.6f));
		}
		entities.add(new Entity(dragon, new Vector3f(10, 0, 10), 0, 0, 0, 1));
		dragon.getTexture().setReflectivity(0.6f);
		tree.getTexture().setReflectivity(0.1f);
		grass.getTexture().setReflectivity(0.1f);
		fern.getTexture().setReflectivity(0.1f);
		
		Light light = new Light(new Vector3f(20000, 20000, 20000),new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(1, 0, loader,new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(0, 0, loader,new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera();	
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()){
			camera.move();
			
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
