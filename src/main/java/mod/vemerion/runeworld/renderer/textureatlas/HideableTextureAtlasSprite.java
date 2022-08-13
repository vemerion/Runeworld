package mod.vemerion.runeworld.renderer.textureatlas;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mojang.blaze3d.platform.NativeImage;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.client.textures.ITextureAtlasSpriteLoader;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper.UnableToFindMethodException;

public class HideableTextureAtlasSprite extends TextureAtlasSprite {

	private static Method uploadMethod;

	private boolean hidden0;

	private HideableTextureAtlasSprite(TextureAtlas pAtlas, Info pSpriteInfo, int pMipLevel, int pStorageX,
			int pStorageY, int pX, int pY, NativeImage pImage) {
		super(pAtlas, pSpriteInfo, pMipLevel, pStorageX, pStorageY, pX, pY, pImage);
	}

	@Override
	public Tickable getAnimationTicker() {
		return new Tickable() {
			@Override
			public void tick() {
				var mc = Minecraft.getInstance();
				var player = mc.player;
				var hidden = player != null
						&& player.getInventory().getArmor(EquipmentSlot.HEAD.getIndex()).is(ModItems.BLOOD_CROWN.get());
				if (hidden != hidden0)
					uploadImage(hidden);
				hidden0 = hidden;
			}
		};
	}

	private void uploadImage(boolean hidden) {
		if (uploadMethod == null) {
			try {
				uploadMethod = ObfuscationReflectionHelper.findMethod(TextureAtlasSprite.class, "m_118375_", int.class,
						int.class, NativeImage[].class);
			} catch (UnableToFindMethodException e) {
				Main.LOGGER
						.warn("Unable to find method 'upload', " + getName() + " will not be rendered correctly: " + e);
				return;
			}
		}
		try {
			uploadMethod.invoke(this, 0, hidden ? 16 : 0, mainImage);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Main.LOGGER
					.warn("Unable to invoke method 'upload', " + getName() + " will not be rendered correctly: " + e);
		}
	}

	public static class Loader implements ITextureAtlasSpriteLoader {

		private static Field heightField;

		@Override
		public TextureAtlasSprite load(TextureAtlas atlas, ResourceManager resourceManager, Info textureInfo,
				Resource resource, int atlasWidth, int atlasHeight, int spriteX, int spriteY, int mipmapLevel,
				NativeImage image) {
			setHeight(textureInfo, textureInfo.height() / 2);
			return new HideableTextureAtlasSprite(atlas, textureInfo, mipmapLevel, atlasWidth, atlasHeight, spriteX,
					spriteY, image);
		}

		private void setHeight(Info textureInfo, int height) {
			if (heightField == null) {
				try {
					heightField = ObfuscationReflectionHelper.findField(Info.class, "f_118424_");
				} catch (UnableToFindMethodException e) {
					Main.LOGGER.warn("Unable to find field 'height', " + textureInfo.name()
							+ " will not be rendered correctly: " + e);
					return;
				}
			}
			try {
				heightField.set(textureInfo, height);
			} catch (IllegalAccessException | IllegalArgumentException e) {
				Main.LOGGER.warn("Unable to set field 'height', " + textureInfo.name()
						+ " will not be rendered correctly: " + e);
			}
		}

	}
}
