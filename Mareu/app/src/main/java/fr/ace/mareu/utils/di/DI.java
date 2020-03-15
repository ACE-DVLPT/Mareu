package fr.ace.mareu.utils.di;

import fr.ace.mareu.api.ApiService;
import fr.ace.mareu.api.FakeApiService;

/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static ApiService service = new FakeApiService();

    /**
     * Get an instance on ApiService
     * @return
     */
    public static ApiService getApiService(){
        return service;
    }

}
