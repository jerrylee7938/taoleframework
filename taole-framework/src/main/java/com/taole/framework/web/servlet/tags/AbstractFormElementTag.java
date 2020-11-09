package com.taole.framework.web.servlet.tags;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.AbstractFormTag;
import org.springframework.web.servlet.tags.form.TagWriter;

public abstract class AbstractFormElementTag extends AbstractFormTag implements DynamicAttributes {

	private static final long serialVersionUID = 931310530251896160L;

	public static final String CLASS_ATTRIBUTE = "class";

	public static final String STYLE_ATTRIBUTE = "style";

	public static final String LANG_ATTRIBUTE = "lang";

	public static final String TITLE_ATTRIBUTE = "title";

	public static final String DIR_ATTRIBUTE = "dir";

	public static final String TABINDEX_ATTRIBUTE = "tabindex";

	public static final String ONCLICK_ATTRIBUTE = "onclick";

	public static final String ONDBLCLICK_ATTRIBUTE = "ondblclick";

	public static final String ONMOUSEDOWN_ATTRIBUTE = "onmousedown";

	public static final String ONMOUSEUP_ATTRIBUTE = "onmouseup";

	public static final String ONMOUSEOVER_ATTRIBUTE = "onmouseover";

	public static final String ONMOUSEMOVE_ATTRIBUTE = "onmousemove";

	public static final String ONMOUSEOUT_ATTRIBUTE = "onmouseout";

	public static final String ONKEYPRESS_ATTRIBUTE = "onkeypress";
	
	public static final String ONCHANGE_ATTRIBUTE = "onchange";

	public static final String ONKEYUP_ATTRIBUTE = "onkeyup";

	public static final String ONKEYDOWN_ATTRIBUTE = "onkeydown";
	
	public static final String ONBLUR_ATTRIBUTE = "onblur";

	private String name;

	private String cssClass;

	private String cssErrorClass;

	private String cssStyle;

	private String lang;

	private String title;

	private String dir;

	private String tabindex;

	private String onclick;

	private String ondblclick;

	private String onchange;

	private String onmousedown;

	private String onmouseup;

	private String onmouseover;

	private String onmousemove;

	private String onmouseout;

	private String onkeypress;

	private String onkeyup;

	private String onkeydown;

	private Map<String, Object> dynamicAttributes;

	private String onblur;
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	
	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	/**
	 * Set the value of the '<code>class</code>' attribute. May be a runtime expression.
	 */
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	/**
	 * Get the value of the '<code>class</code>' attribute. May be a runtime expression.
	 */
	protected String getCssClass() {
		return this.cssClass;
	}

	/**
	 * The CSS class to use when the field bound to a particular tag has errors. May be a runtime expression.
	 */
	public void setCssErrorClass(String cssErrorClass) {
		this.cssErrorClass = cssErrorClass;
	}

	/**
	 * The CSS class to use when the field bound to a particular tag has errors. May be a runtime expression.
	 */
	protected String getCssErrorClass() {
		return this.cssErrorClass;
	}

	/**
	 * Set the value of the '<code>style</code>' attribute. May be a runtime expression.
	 */
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	/**
	 * Get the value of the '<code>style</code>' attribute. May be a runtime expression.
	 */
	protected String getCssStyle() {
		return this.cssStyle;
	}

	/**
	 * Set the value of the '<code>lang</code>' attribute. May be a runtime expression.
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * Get the value of the '<code>lang</code>' attribute. May be a runtime expression.
	 */
	protected String getLang() {
		return this.lang;
	}

	/**
	 * Set the value of the '<code>title</code>' attribute. May be a runtime expression.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the value of the '<code>title</code>' attribute. May be a runtime expression.
	 */
	protected String getTitle() {
		return this.title;
	}

	/**
	 * Set the value of the '<code>dir</code>' attribute. May be a runtime expression.
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * Get the value of the '<code>dir</code>' attribute. May be a runtime expression.
	 */
	protected String getDir() {
		return this.dir;
	}

	/**
	 * Set the value of the '<code>tabindex</code>' attribute. May be a runtime expression.
	 */
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	/**
	 * Get the value of the '<code>tabindex</code>' attribute. May be a runtime expression.
	 */
	protected String getTabindex() {
		return this.tabindex;
	}

	/**
	 * Set the value of the '<code>onclick</code>' attribute. May be a runtime expression.
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * Get the value of the '<code>onclick</code>' attribute. May be a runtime expression.
	 */
	protected String getOnclick() {
		return this.onclick;
	}

	/**
	 * Set the value of the '<code>ondblclick</code>' attribute. May be a runtime expression.
	 */
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * Get the value of the '<code>ondblclick</code>' attribute. May be a runtime expression.
	 */
	protected String getOndblclick() {
		return this.ondblclick;
	}

	/**
	 * Set the value of the '<code>onmousedown</code>' attribute. May be a runtime expression.
	 */
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * Get the value of the '<code>onmousedown</code>' attribute. May be a runtime expression.
	 */
	protected String getOnmousedown() {
		return this.onmousedown;
	}

	/**
	 * Set the value of the '<code>onmouseup</code>' attribute. May be a runtime expression.
	 */
	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	/**
	 * Get the value of the '<code>onmouseup</code>' attribute. May be a runtime expression.
	 */
	protected String getOnmouseup() {
		return this.onmouseup;
	}

	/**
	 * Set the value of the '<code>onmouseover</code>' attribute. May be a runtime expression.
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * Get the value of the '<code>onmouseover</code>' attribute. May be a runtime expression.
	 */
	protected String getOnmouseover() {
		return this.onmouseover;
	}

	/**
	 * Set the value of the '<code>onmousemove</code>' attribute. May be a runtime expression.
	 */
	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	/**
	 * Get the value of the '<code>onmousemove</code>' attribute. May be a runtime expression.
	 */
	protected String getOnmousemove() {
		return this.onmousemove;
	}

	/**
	 * Set the value of the '<code>onmouseout</code>' attribute. May be a runtime expression.
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * Get the value of the '<code>onmouseout</code>' attribute. May be a runtime expression.
	 */
	protected String getOnmouseout() {
		return this.onmouseout;
	}

	/**
	 * Set the value of the '<code>onkeypress</code>' attribute. May be a runtime expression.
	 */
	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	/**
	 * Get the value of the '<code>onkeypress</code>' attribute. May be a runtime expression.
	 */
	protected String getOnkeypress() {
		return this.onkeypress;
	}

	/**
	 * Set the value of the '<code>onkeyup</code>' attribute. May be a runtime expression.
	 */
	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	/**
	 * Get the value of the '<code>onkeyup</code>' attribute. May be a runtime expression.
	 */
	protected String getOnkeyup() {
		return this.onkeyup;
	}

	/**
	 * Set the value of the '<code>onkeydown</code>' attribute. May be a runtime expression.
	 */
	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	/**
	 * Get the value of the '<code>onkeydown</code>' attribute. May be a runtime expression.
	 */
	protected String getOnkeydown() {
		return this.onkeydown;
	}

	/**
	 * Get the map of dynamic attributes.
	 */
	protected Map<String, Object> getDynamicAttributes() {
		return this.dynamicAttributes;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		if (this.dynamicAttributes == null) {
			this.dynamicAttributes = new HashMap<String, Object>();
		}
		if (!isValidDynamicAttribute(localName, value)) {
			throw new IllegalArgumentException("Attribute " + localName + "=\"" + value + "\" is not allowed");
		}
		dynamicAttributes.put(localName, value);
	}

	/**
	 * Whether the given name-value pair is a valid dynamic attribute.
	 */
	protected boolean isValidDynamicAttribute(String localName, Object value) {
		return true;
	}

	/**
	 * Writes the default attributes configured via this base class to the supplied {@link TagWriter}. Subclasses should call this when they want the base attribute set to be written to the output.
	 */
	protected void writeDefaultAttributes(TagWriter tagWriter) throws JspException {
		writeOptionalAttribute(tagWriter, "id", resolveId());
		writeOptionalAttribute(tagWriter, "name", getName());
		writeOptionalAttributes(tagWriter);
	}

	/**
	 * Writes the optional attributes configured via this base class to the supplied {@link TagWriter}. The set of optional attributes that will be rendered includes any non-standard dynamic attributes. Called by
	 * {@link #writeDefaultAttributes(TagWriter)}.
	 */
	protected void writeOptionalAttributes(TagWriter tagWriter) throws JspException {
		tagWriter.writeOptionalAttributeValue(CLASS_ATTRIBUTE, resolveCssClass());
		tagWriter.writeOptionalAttributeValue(STYLE_ATTRIBUTE, ObjectUtils.getDisplayString(evaluate("cssStyle", getCssStyle())));
		writeOptionalAttribute(tagWriter, LANG_ATTRIBUTE, getLang());
		writeOptionalAttribute(tagWriter, TITLE_ATTRIBUTE, getTitle());
		writeOptionalAttribute(tagWriter, DIR_ATTRIBUTE, getDir());
		writeOptionalAttribute(tagWriter, TABINDEX_ATTRIBUTE, getTabindex());
		writeOptionalAttribute(tagWriter, ONCLICK_ATTRIBUTE, getOnclick());
		writeOptionalAttribute(tagWriter, ONDBLCLICK_ATTRIBUTE, getOndblclick());
		writeOptionalAttribute(tagWriter, ONMOUSEDOWN_ATTRIBUTE, getOnmousedown());
		writeOptionalAttribute(tagWriter, ONMOUSEUP_ATTRIBUTE, getOnmouseup());
		writeOptionalAttribute(tagWriter, ONMOUSEOVER_ATTRIBUTE, getOnmouseover());
		writeOptionalAttribute(tagWriter, ONMOUSEMOVE_ATTRIBUTE, getOnmousemove());
		writeOptionalAttribute(tagWriter, ONMOUSEOUT_ATTRIBUTE, getOnmouseout());
		writeOptionalAttribute(tagWriter, ONKEYPRESS_ATTRIBUTE, getOnkeypress());
		writeOptionalAttribute(tagWriter, ONKEYUP_ATTRIBUTE, getOnkeyup());
		writeOptionalAttribute(tagWriter, ONKEYDOWN_ATTRIBUTE, getOnkeydown());
		writeOptionalAttribute(tagWriter, ONCHANGE_ATTRIBUTE, getOnchange());
		writeOptionalAttribute(tagWriter, ONBLUR_ATTRIBUTE, getOnblur());
		if (!CollectionUtils.isEmpty(this.dynamicAttributes)) {
			for (String attr : this.dynamicAttributes.keySet()) {
				tagWriter.writeOptionalAttributeValue(attr, getDisplayString(this.dynamicAttributes.get(attr)));
			}
		}
	}

	/**
	 * Gets the appropriate CSS class to use based on the state of the current {@link org.springframework.web.servlet.support.BindStatus} object.
	 */
	protected String resolveCssClass() throws JspException {
		if (StringUtils.hasText(getCssErrorClass())) {
			return ObjectUtils.getDisplayString(evaluate("cssErrorClass", getCssErrorClass()));
		} else {
			return ObjectUtils.getDisplayString(evaluate("cssClass", getCssClass()));
		}
	}

	/**
	 * Determine the '<code>id</code>' attribute value for this tag, autogenerating one if none specified.
	 * 
	 * @see #getId()
	 * @see #autogenerateId()
	 */
	protected String resolveId() throws JspException {
		Object id = evaluate("id", getId());
		if (id != null) {
			String idString = id.toString();
			return (StringUtils.hasText(idString) ? idString : null);
		}
		return autogenerateId();
	}

	protected String autogenerateId() throws JspException {
		return StringUtils.deleteAny(getName(), "[]");
	}

	@Override
	protected abstract int writeTagContent(TagWriter tagWriter) throws JspException;

}
