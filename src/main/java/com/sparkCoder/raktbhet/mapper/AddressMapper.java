package com.sparkCoder.raktbhet.mapper;

import com.sparkCoder.raktbhet.dto.AddressDto;
import com.sparkCoder.raktbhet.entity.AddressEntity;
import org.springframework.stereotype.Component;
@Component
public class AddressMapper {

        public static AddressEntity toEntity(AddressDto dto) {

            if (dto == null) {
                return null;
            }

            AddressEntity address = new AddressEntity();

            address.setId(dto.getId());
            address.setCity(dto.getCity());
            address.setState(dto.getState());
            address.setCountry(dto.getCountry());
            address.setLandMark(dto.getLandMark());
            address.setPincode(dto.getPincode());

            return address;
        }

        public static AddressDto toDto(AddressEntity entity) {

            if (entity == null) {
                return null;
            }

            AddressDto dto = new AddressDto();

            dto.setId(entity.getId());
            dto.setCity(entity.getCity());
            dto.setState(entity.getState());
            dto.setCountry(entity.getCountry());
            dto.setLandMark(entity.getLandMark());
            dto.setPincode(entity.getPincode());

            return dto;
        }

    }


