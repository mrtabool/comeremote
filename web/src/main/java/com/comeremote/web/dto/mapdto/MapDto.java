package com.comeremote.web.dto.mapdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapDto<K, V> {

    K key;
    V value;

    public static <K,V> MapDto<K,V> of(K key, V value) {
        return new MapDto<>(key,value);
    }
}
