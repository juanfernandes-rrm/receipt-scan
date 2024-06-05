package br.ufpr.tads.repceiptscan.dto;

import br.ufpr.tads.repceiptscan.dto.response.ItemDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductsDTO {

    private List<ItemDTO> items;
    private UUID branchId;

}
