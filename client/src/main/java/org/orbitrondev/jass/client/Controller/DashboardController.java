package org.orbitrondev.jass.client.Controller;

import javafx.application.Platform;
import org.orbitrondev.jass.client.Model.DashboardModel;
import org.orbitrondev.jass.client.View.DashboardView;
import org.orbitrondev.jass.lib.MVC.Controller;

public class DashboardController extends Controller<DashboardModel, DashboardView> {
    protected DashboardController(DashboardModel model, DashboardView view) {
        super(model, view);

        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(event -> Platform.exit());
    }
}
