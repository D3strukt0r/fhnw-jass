package org.orbitrondev.jass.client.Message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.client.Entity.LoginEntity;
import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.client.Utils.DatabaseUtil;
import org.orbitrondev.jass.lib.Message.ChangePasswordData;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.sql.SQLException;

/**
 * Overwrite the password of the currently logged in user.
 */
public class ChangePassword extends Message {
    private static final Logger logger = LogManager.getLogger(ChangePassword.class);
    private ChangePasswordData data;

    public ChangePassword(MessageData rawData) {
        super(rawData);
        data = (ChangePasswordData) rawData;
    }

    @Override
    public boolean process(BackendUtil backendUtil) {
        backendUtil.send(this);

        Message result = backendUtil.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        if (resultData.getResult()) {
            // Data is final, so create a new object.
            LoginEntity login = (LoginEntity) ServiceLocator.get("login");
            LoginEntity newLogin = new LoginEntity(login.getUsername(), data.getNewPassword(), login.getToken());

            // Replace the ServiceLocator with the new login.
            ServiceLocator.remove("login");
            ServiceLocator.add(newLogin);

            // Also remove the old login from the database and replace with the new one.
            DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
            try {
                db.getLoginDao().delete(login);
                db.getLoginDao().create(newLogin);
            } catch (SQLException e) {
                logger.error("Couldn't save login data to local database.");
            }
        }
        return resultData.getResult();
    }
}
