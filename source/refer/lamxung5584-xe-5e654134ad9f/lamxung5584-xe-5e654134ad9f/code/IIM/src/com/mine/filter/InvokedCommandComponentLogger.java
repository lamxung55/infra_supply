/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.filter;

import com.google.gson.GsonBuilder;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.apache.log4j.Logger;

/**
 *
 * @author anhdt8
 */
public class InvokedCommandComponentLogger implements PhaseListener {

    private static final Logger logger = Logger.getLogger("LogActionFilter");

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        // NOOP. The view hasn't been restored yet at that point, so the component tree wouldn't be available anyway.
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();

        if (context.isPostback()) {
            UICommand component = findInvokedCommandComponent(context);

            if (component != null) {
                String jResult = new GsonBuilder().create().toJson(component);
                logger.info("\n\n================\n\n" + jResult + "\n\n================\n\n");
                String methodExpression = component.getActionExpression().getExpressionString();
                logger.info("Method expression of the action being invoked: " + methodExpression);
            }
        }
    }

    private UICommand findInvokedCommandComponent(FacesContext context) {
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        Set<String> clientIds = new HashSet<>();

        if (context.getPartialViewContext().isAjaxRequest()) {
            clientIds.add(params.get("javax.faces.source")); // This covers <f:ajax> inside UICommand.
        } else {
            for (Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey().equals(entry.getValue())) { // This covers UIForm and UICommand components.
                    clientIds.add(entry.getKey());
                }
            }
        }

        EnumSet<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
        final UICommand[] found = new UICommand[1];
        context.getViewRoot().visitTree(VisitContext.createVisitContext(context, clientIds, hints), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent target) {
                if (target instanceof UICommand) {
                    found[0] = (UICommand) target;
                    return VisitResult.COMPLETE;
                } else {
                    return VisitResult.ACCEPT;
                }
            }
        });

        return found[0];
    }

}
