package surveasy.domain.response.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.TransactionAwareProxyFactory;

import java.util.Collection;
import java.util.List;

public class CustomItemWriter<T> implements ItemWriter<T> {

    List<T> output = TransactionAwareProxyFactory.createTransactionalList();

    public void write(Chunk<? extends T> items) throws Exception {
        output.addAll((Collection<? extends T>) items);
    }

    public List<T> getOutput() {
        return output;
    }
}
