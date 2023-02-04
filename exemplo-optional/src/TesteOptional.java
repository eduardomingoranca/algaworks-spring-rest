import com.algaworks.model.Seguro;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class TesteOptional {
    public static void main(String[] args) {
        Seguro seguro = new Seguro("Total com franquia reduzida", new BigDecimal("600"));

//         criando um optional do tipo do objeto informado
//        Optional<Seguro> seguroOptional = of(seguro);

//        criando um optional vazio
//        Seguro seguro1 = null;
//        Optional<Seguro> seguroOptionalVazio = ofNullable(seguro1);
//        Optional<Seguro> seguroOptionalVazio2 = Optional.empty();

        Optional<Seguro> seguroOptional = Optional.of(seguro);

        seguroOptional.map(Seguro::getValorFranquia).ifPresent(System.out::println);

    }
}
