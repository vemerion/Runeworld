package mod.vemerion.runeworld.model;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModModelRenderer extends ModelRenderer {

	public float startRotX, startRotY, startRotZ;

	public ModModelRenderer(Model model, int texOffX, int texOffY) {
		super(model, texOffX, texOffY);
	}

	public void initRot(float rotX, float rotY, float rotZ) {
		rotateAngleX = rotX;
		startRotX = rotX;
		rotateAngleY = rotY;
		startRotY = rotY;
		rotateAngleZ = rotZ;
		startRotZ = rotZ;
	}
	
	public void resetRot() {
		rotateAngleX = startRotX;
		rotateAngleY = startRotY;
		rotateAngleZ = startRotZ;
	}
}
