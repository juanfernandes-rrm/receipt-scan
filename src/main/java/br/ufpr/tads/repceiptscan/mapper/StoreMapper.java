package br.ufpr.tads.repceiptscan.mapper;

import br.ufpr.tads.repceiptscan.model.Address;
import br.ufpr.tads.repceiptscan.model.City;
import br.ufpr.tads.repceiptscan.model.StateEnum;
import br.ufpr.tads.repceiptscan.model.Store;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {

    private static final String STORE_NAME = "div#conteudo > div:nth-child(2) > div:nth-child(1)";
    private static final String STORE_CNPJ = "div#conteudo > div:nth-child(2) > div:nth-child(2)";
    private static final String STORE_ADDRESS = "div#conteudo > div:nth-child(2) > div:nth-child(3)";
    public static final String REGEX_TO_REMOVE_CNPJ_MASK = "[^0-9]";

    public Store mapStore(Document document) {
        Store store = new Store();
        store.setName(document.select(STORE_NAME).text());
        store.setCNPJ(document.select(STORE_CNPJ).text().replace("CNPJ: ", "").replaceAll(REGEX_TO_REMOVE_CNPJ_MASK, ""));
        store.setAddress(extractAddress(document.select(STORE_ADDRESS).text()));
        return store;
    }

    private Address extractAddress(String addressString) {
        Address address = new Address();
        String[] split = addressString.split(", ");
        address.setStreet(split[0]);
        address.setNumber(split[1]);
        address.setNeighborhood(split[3]);
        address.setCity(extractCity(split[4], split[5]));
        return address;
    }

    private City extractCity(String cityName, String stateName) {
        City city = new City();
        city.setName(cityName);
        city.setState(StateEnum.valueOf(stateName));
        return city;
    }
}

