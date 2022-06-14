package com.carlettos.fmapf.interfaces;

import java.util.Optional;

import com.carlettos.fmapf.essence.Essence;

public interface IEssenceGetter {
	default Optional<Essence> getEssence(){
		return Optional.empty();
	};
}
