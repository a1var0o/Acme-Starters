
package acme.datatypes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntTuple {

	private Integer	key;

	private Integer	value;


	public IntTuple(final Integer key, final long value) {
		this.key = key;
		this.value = (int) value;
	}

}
