package com.markchan.carrier.data.dao.impl;

import android.content.Context;
import com.markchan.carrier.data.CarrierDataConstant;
import com.markchan.carrier.data.dao.FontEntityDao;
import com.markchan.carrier.data.entity.DaoMaster;
import com.markchan.carrier.data.entity.DaoMaster.DevOpenHelper;
import com.markchan.carrier.data.entity.DaoMaster.OpenHelper;
import com.markchan.carrier.data.entity.DaoSession;
import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.mapper.FontEntityDataMapper;
import com.markchan.carrier.domain.Font;
import java.util.List;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2017/9/22
 */
public class FontEntityDaoImpl implements FontEntityDao {

    private final FontEntityDataMapper mFontEntityDataMapper;
    private final DaoSession mDaoSession;
    private final com.markchan.carrier.data.entity.FontEntityDao mFontEntityDao;

    public FontEntityDaoImpl(Context context, FontEntityDataMapper fontEntityDataMapper) {
        mFontEntityDataMapper = fontEntityDataMapper;
        OpenHelper openHelper = new DevOpenHelper(context, CarrierDataConstant.DB_NAME);
        mDaoSession = new DaoMaster(openHelper.getWritableDb()).newSession();
        mFontEntityDao = mDaoSession.getFontEntityDao();
    }

    @Override
    public boolean updateFont(Font font) {
        if (font == null) {
            throw new NullPointerException();
        }

        return updateFontEntity(mFontEntityDataMapper.retransform(font));
    }

    @Override
    public boolean deleteFont(Font font) {
        if (font == null) {
            throw new NullPointerException();
        }

        return deleteFontEntity(mFontEntityDataMapper.retransform(font));
    }

    @Override
    public long insertFontEntity(FontEntity fontEntity) {
        return fontEntity != null ? mFontEntityDao.insert(fontEntity) : 0L;
    }

    @Override
    public boolean updateFontEntity(FontEntity fontEntity) {
        if (fontEntity == null) {
            return false;
        }

        try {
            mFontEntityDao.update(fontEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(FontEntity fontEntity) {
        if (fontEntity == null) {
            return false;
        }

        try {
            mFontEntityDao.save(fontEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteFontEntity(FontEntity fontEntity) {
        if (fontEntity == null) {
            return false;
        }

        try {
            mFontEntityDao.delete(fontEntity);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public FontEntity queryFontEntityById(long fontId) {
        return mFontEntityDao.load(fontId);
    }

    @Override
    public List<FontEntity> queryFontEntities(final int dataSource) {
        QueryBuilder<FontEntity> qb = mFontEntityDao.queryBuilder();

        List<FontEntity> fontEntities = qb.list();

        return fontEntities != null && !fontEntities.isEmpty() ? fontEntities : null;
    }
}
