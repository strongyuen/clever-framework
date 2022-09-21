package io.github.toquery.framework.crud.service.impl;

import io.github.toquery.framework.crud.service.AppBFFService;
import io.github.toquery.framework.core.entity.AppBaseEntity;
import io.github.toquery.framework.jpa.repository.AppJpaBaseRepository;

/**
 *
 */
public abstract class AppBFFServiceImpl<E extends AppBaseEntity, D extends AppJpaBaseRepository<E>> extends AppBaseServiceImpl<E, D> implements AppBFFService {

}
