package com.example.aplicacion_cine.PokeapiService;

import com.example.aplicacion_cine.models.PokemonRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeapiService {
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon();
}
