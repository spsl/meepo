package org.feisoft.jta.image.resolvers;

import net.sf.jsqlparser.JSQLParserException;
import org.feisoft.common.utils.SqlpraserUtils;
import org.feisoft.jta.image.BackInfo;
import org.feisoft.jta.image.Image;

import javax.transaction.xa.XAException;
import java.sql.SQLException;
import java.util.List;

public class UpdateImageResolvers extends BaseResolvers {

    UpdateImageResolvers(String orginSql, BackInfo backInfo)
    {
        this.orginSql =orginSql;
        this.backInfo = backInfo;
    }


    @Override
    public Image genBeforeImage() throws SQLException, JSQLParserException, XAException {
        return genImage();
    }

    @Override
    public Image genAfterImage() throws SQLException, XAException, JSQLParserException {
        return genImage();
    }

    @Override
    public String getTable() throws JSQLParserException, XAException {

        List<String> tables = SqlpraserUtils.name_update_table(orginSql);
        if (tables.size() > 1) {
            throw new XAException("Update.UnsupportMultiTables");
        }
        return tables.get(0);
    }

    @Override
    public String getSqlWhere() throws JSQLParserException {
        return SqlpraserUtils.name_update_where(orginSql);
    }


    @Override
    public List<String> getColumnList() throws JSQLParserException {
        return SqlpraserUtils.name_update_column(orginSql);
    }

    @Override
    public String getLockedSet() {

        return beforeImageSql;
    }
}
