import com.algaworks.model.Seguro;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class TesteOptional {
    public static void main(String[] args) {
        Seguro seguro = new Seguro("Total com franquia reduzida", new BigDecimal("600"));

        Optional<Seguro> seguroOptional = ofNullable(seguro);

        seguroOptional.map(Seguro::getValorFranquia).ifPresent(System.out::println);

    }
}
