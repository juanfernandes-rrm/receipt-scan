package br.ufpr.tads.repceiptscan.model;

public enum StateEnum {

    PR("Paraná"),
    SC("Santa Catarina"),
    RS("Rio Grande do Sul"),
    SP("São Paulo"),
    RJ("Rio de Janeiro"),
    MG("Minas Gerais"),
    ES("Espírito Santo"),
    BA("Bahia"),
    SE("Sergipe"),
    AL("Alagoas"),
    PE("Pernambuco"),
    PB("Paraíba"),
    RN("Rio Grande do Norte"),
    CE("Ceará"),
    PI("Piauí"),
    MA("Maranhão"),
    PA("Pará"),
    AP("Amapá"),
    AM("Amazonas"),
    RR("Roraima"),
    AC("Acre"),
    RO("Rondônia"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    GO("Goiás"),
    DF("Distrito Federal"),
    TO("Tocantins");

    private final String state;

    StateEnum(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }


}
