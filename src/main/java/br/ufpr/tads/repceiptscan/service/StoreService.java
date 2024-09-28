package br.ufpr.tads.repceiptscan.service;

import br.ufpr.tads.repceiptscan.model.Address;
import br.ufpr.tads.repceiptscan.model.City;
import br.ufpr.tads.repceiptscan.model.Receipt;
import br.ufpr.tads.repceiptscan.model.Store;
import br.ufpr.tads.repceiptscan.repository.AddressRepository;
import br.ufpr.tads.repceiptscan.repository.CityRepository;
import br.ufpr.tads.repceiptscan.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CityRepository cityRepository;

    public void saveOrGetStore(Receipt receipt) {
        Store store = receipt.getStore();
        Store saveStore = storeRepository.findByCNPJ(store.getCNPJ()).orElseGet(() -> {
            saveOrGetAddress(store.getAddress());
            return storeRepository.save(store);
        });
        receipt.setStore(saveStore);
    }

    private void saveOrGetAddress(Address address) {
        City city = cityRepository.findByNameAndState(address.getCity().getName(), address.getCity().getState())
                .orElseGet(() -> cityRepository.save(address.getCity()));
        address.setCity(city);
    }
}
