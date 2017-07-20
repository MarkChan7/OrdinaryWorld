package com.markchan.carrier.domain.dao;

import com.markchan.carrier.domain.Font;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/20
 */
public interface FontDao {

    boolean update(Font font);

    boolean delete(Font font);
}
