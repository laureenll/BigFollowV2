package miage.fr.gestionprojet.models;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.TableInfo;

/**
 * Created by ughostephan on 21/02/2018.
 */

public abstract class ActiveModel extends Model {

    private final TableInfo mTableInfo;

    ActiveModel() {
        super();
        mTableInfo = Cache.getTableInfo(getClass());
        System.out.println(mTableInfo);
    }

    public String getTableName() {
        return mTableInfo.getTableName();
    }

}
