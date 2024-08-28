package br.ufpr.tads.repceiptscan.service;

import br.ufpr.tads.repceiptscan.model.Address;
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

    public Store saveOrGetStore(Store store) {
        return storeRepository.findByCNPJ(store.getCNPJ()).orElseGet(() -> {
            saveAddress(store.getAddress());
            return storeRepository.save(store);
        });
    }

    private void saveAddress(Address address) {
        cityRepository.save(address.getCity());
        addressRepository.save(address);
    }
}
