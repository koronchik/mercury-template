/*
 * This program is part of the OpenCms Mercury Template.
 *
 * Copyright (c) Alkacon Software GmbH & Co. KG (http://www.alkacon.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package alkacon.mercury.template.subscriptionmanagement;

import org.opencms.i18n.CmsMessageContainer;
import org.opencms.main.CmsLog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;

import alkacon.mercury.template.subscriptionmanagement.CmsSubscriptionParameterHandler.SubscriptionAction;

/** Action to unsubscribe a webuser when he confirms a requested unsubscription. */
public class CmsSubscriptionActionConfirmUnsubscription implements I_CmsSubscriptionAction {

    /** The log object for this class. */
    private static final Log LOG = CmsLog.getLog(CmsSubscriptionActionConfirmUnsubscription.class);

    /** The subscription manager. */
    private CmsSubscriptionManager m_manager;

    /**
     * Default constructor.
     *
     * @param manager the subscription manager to use.
     */
    public CmsSubscriptionActionConfirmUnsubscription(CmsSubscriptionManager manager) {

        m_manager = manager;

    }

    /**
     * @see alkacon.mercury.template.subscriptionmanagement.I_CmsSubscriptionAction#getName()
     */
    @Override
    public String getName() {

        return SubscriptionAction.CONFIRM_UNSUBSCRIPTION.toString();
    }

    /**
     * @see alkacon.mercury.template.subscriptionmanagement.I_CmsSubscriptionAction#getParamValue()
     */
    @Override
    public String getParamValue() {

        return String.valueOf(SubscriptionAction.CONFIRM_UNSUBSCRIPTION.ordinal());
    }

    /**
     * Unsubscribes a webuser from the requested groups.
     *
     * @see alkacon.mercury.template.subscriptionmanagement.I_CmsSubscriptionAction#perform(CmsSubscriptionParameterHandler)
     */
    @Override
    public List<CmsMessageContainer> perform(CmsSubscriptionParameterHandler paramHandler) {

        List<CmsMessageContainer> errors = new ArrayList<>();
        String email = paramHandler.getEmail();
        for (String group : paramHandler.getRequestedGroups()) {
            if (!m_manager.unregisterUser(email, group)) {
                LOG.warn("Could not unregister subscriber '" + email + "' for group '" + group + "'.");
                errors.add(
                    new CmsMessageContainer(Messages.get(), Messages.GUI_FAILED_UNSUBSCRIPTION_FOR_2, email, group));
            }
        }
        return errors;
    }
}
