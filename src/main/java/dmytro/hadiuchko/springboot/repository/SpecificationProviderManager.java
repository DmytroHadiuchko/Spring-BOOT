package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.repository.book.SpecificationProvider;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecificationProvider(String key);
}
