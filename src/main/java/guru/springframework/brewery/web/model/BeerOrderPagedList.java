/*
 *  Copyright 2019 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package guru.springframework.brewery.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BeerOrderPagedList extends PageImpl<BeerOrderDto> {

    /**
     * Tenemos que definir un constructor con el Tag @JsonCreator para que sepa como convertir el mensaje Que va incluido en
     * una Page, lo que provoca la siguiente excepción:
     * <p>
     * org.springframework.http.converter.HttpMessageConversionException: Type definition error:
     * [simple type, class org.springframework.data.domain.Pageable]
     *
     * @JsonCreator Marker annotation that can be used to define constructors and factory methods as one to use for instantiating
     * new instances of the associated class.
     * <p>
     * JsonCreator.Mode.PROPERTIES Mode indicates that the argument(s) for creator are to be bound from matching properties
     * of incoming Object value, using creator argument names (explicit or implicit) to match incoming Object properties to
     * arguments.
     */

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BeerOrderPagedList(@JsonProperty("content") List<BeerOrderDto> content,
                              @JsonProperty("number") int number,
                              @JsonProperty("size") int size,
                              @JsonProperty("totalElements") Long totalElements,
                              @JsonProperty("pageable") JsonNode pageable,
                              @JsonProperty("last") boolean last,
                              @JsonProperty("totalPages") int totalPages,
                              @JsonProperty("sort") JsonNode sort,
                              @JsonProperty("first") boolean first,
                              @JsonProperty("numberOfElements") int numberOfElements) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    public BeerOrderPagedList(List<BeerOrderDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerOrderPagedList(List<BeerOrderDto> content) {
        super(content);
    }
}
