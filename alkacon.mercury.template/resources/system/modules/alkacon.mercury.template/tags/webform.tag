<%@ tag
    pageEncoding="UTF-8"
    display-name="webform"
    body-content="empty"
    trimDirectiveWhitespaces="true"
    description="Generates a Mercury Webform." %>

<%@ attribute name="webform" type="java.lang.Object" required="true"
    description="The object to initialze the form with.
    This can be an XML content or a CmsResource representing a form, or a path to a form XML configuration." %>

<%@ attribute name="bookingInfo" type="java.lang.Object" required="false"
    description="For booking forms, an object thet points to the additional booking settings.
    This can be an XML content or a path to a XML configuration that contains booking information." %>

<%@ attribute name="formId" type="java.lang.Object" required="false"
    description="If set, the configId for the form is generated from the hashCode of this Object." %>

<%@ attribute name="formCssWrapper" type="java.lang.String" required="false"
    description="Optional CSS style wrapper for the generated form element." %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mercury" tagdir="/WEB-INF/tags/mercury" %>


<%-- ###### Generate the form ###### --%>
<fmt:setLocale value="${cms.locale}"/>
<cms:bundle basename="alkacon.mercury.template.messages">
    <mercury:webform-vars
        webform="${webform}"
        bookingInfo="${bookingInfo}"
        formId="${formId}">

        <c:if test="${form.userCanManage and cms.isEditMode}">
            <div class="subelement"><%----%>
                <a class="btn btn-block oct-meta-info" href="<cms:link>${adminLink}?formmanage=${formId.hashCode()}</cms:link>"><%----%>
                    <fmt:message key="msg.page.form.button.submissions.manage" />
                </a><%----%>
            </div><%----%>
        </c:if>

        <c:if test="${not empty formCssWrapper}">
            ${form.addExtraConfig("formCssWrapper", formCssWrapper)}
        </c:if>
        ${form.createFormHandler(pageContext).createForm()}
    </mercury:webform-vars>
</cms:bundle>