package com.carlettos.fmapf.listas;

import com.carlettos.fmapf.CarlettosMod;
import com.carlettos.fmapf.screen.MesitaScreen;
import com.carlettos.fmapf.screen.MesitaScreenHandler;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ScreenHandlers {
	public static ScreenHandlerType<MesitaScreenHandler> MESITA;
	
	public static void registrar() {
		MESITA = Registry.register(Registry.SCREEN_HANDLER, new Identifier(CarlettosMod.ID, "mesita"), new ScreenHandlerType<MesitaScreenHandler>(MesitaScreenHandler::new));
		HandledScreens.register(MESITA, MesitaScreen::new);
	}
}
