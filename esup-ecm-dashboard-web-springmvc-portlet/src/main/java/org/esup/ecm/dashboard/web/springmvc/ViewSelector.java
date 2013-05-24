package org.esup.ecm.dashboard.web.springmvc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import org.springframework.stereotype.Service;

@Service
public class ViewSelector {
	private List<Pattern> mobileDeviceRegexes = null;

	/**
	 * Set a list of regex patterns for user agents which should be considered
	 * to be mobile devices.
	 * 
	 * @param patterns
	 */
	@Resource(name="mobileDeviceRegexes")
	public void setMobileDeviceRegexes(List<String> patterns) {
		this.mobileDeviceRegexes = new ArrayList<Pattern>();
		for (String pattern : patterns) {
			this.mobileDeviceRegexes.add(Pattern.compile(pattern));
		}
	}

	public String getViewName(PortletRequest request, String viewName) {
		String userAgent = request.getProperty("user-agent");

		// check to see if this is a mobile device
		if (this.mobileDeviceRegexes != null && userAgent != null) {
			for (Pattern regex : this.mobileDeviceRegexes) {
				if (regex.matcher(userAgent).matches()) {
					return viewName + "_mobile";
				}
			}
		}

		// otherwise check the portlet window state
		WindowState state = request.getWindowState();
		if (WindowState.MAXIMIZED.equals(state)) {
			return viewName + "_wide";
		} else {
			return viewName + "_narrow";
		}

	}

	public String getEventListViewName(PortletRequest request) {
		return "ajaxEventList";
	}
}
