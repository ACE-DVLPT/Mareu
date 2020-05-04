package fr.ace.mareu.utils.di;

import fr.ace.mareu.api.ApiService;
import fr.ace.mareu.api.ApiServiceImpl;

/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static ApiService service = new ApiServiceImpl();

    /**
     * @return service -> an instance of ApiService
     */
    public static ApiService getApiService(){
        return service;
    }

}
