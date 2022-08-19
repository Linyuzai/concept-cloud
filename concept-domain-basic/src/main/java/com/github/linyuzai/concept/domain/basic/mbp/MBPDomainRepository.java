package com.github.linyuzai.concept.domain.basic.mbp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.linyuzai.concept.domain.basic.DomainFacadeAdapter;
import com.github.linyuzai.concept.domain.basic.DomainObject;
import com.github.linyuzai.concept.domain.basic.DomainRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class MBPDomainRepository<T extends DomainObject, P> implements DomainRepository<T> {

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void add(Collection<? extends T> objects) {
        objects.stream().map(getFacadeAdapter()::do2po).forEach(getBaseMapper()::insert);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(Collection<? extends T> objects) {
        objects.stream().map(getFacadeAdapter()::do2po).forEach(getBaseMapper()::updateById);
    }

    @Override
    public void delete(Collection<? extends String> uniques) {
        getBaseMapper().deleteBatchIds(uniques);
    }

    @Override
    public T get(String unique) {
        return getFacadeAdapter().po2do(getBaseMapper().selectById(unique));
    }

    @Override
    public Collection<? extends T> select(Collection<? extends String> uniques) {
        return getBaseMapper().selectBatchIds(uniques)
                .stream()
                .map(getFacadeAdapter()::po2do)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends T> all() {
        return getBaseMapper().selectList(null)
                .stream()
                .map(getFacadeAdapter()::po2do)
                .collect(Collectors.toList());
    }

    public abstract BaseMapper<P> getBaseMapper();

    public abstract DomainFacadeAdapter<T, P> getFacadeAdapter();
}
