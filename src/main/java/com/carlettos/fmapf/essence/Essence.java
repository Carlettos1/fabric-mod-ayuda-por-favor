package com.carlettos.fmapf.essence;

import java.util.Optional;

import com.carlettos.fmapf.CarlettosMod;

public enum Essence {
	MAGIC,
	MINERAL,
	PALITO;
	
	public void use() {
		CarlettosMod.LOGGER.info(this.name() + " usado");
	}
	
	public Optional<Essence> toOptional(){
		return Optional.of(this);
	}
}
