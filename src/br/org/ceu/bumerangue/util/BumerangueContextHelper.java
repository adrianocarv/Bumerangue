package br.org.ceu.bumerangue.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;

public final class BumerangueContextHelper implements ApplicationContextAware, MessageSourceAware {
    
    private static final BumerangueContextHelper INSTANCE = new BumerangueContextHelper();
    public BumerangueContextHelper(){
        // do nothing
    }
    public static BumerangueContextHelper getInstance() {
        return INSTANCE;
    }
    protected ApplicationContext appContext;
    final public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        getInstance().appContext = applicationContext; }
    public ApplicationContext getApplicationContext(){ return appContext;}

    // -- Spring MessageSource (i18n) ----------------------------------------------------------------------
    protected MessageSourceAccessor messageSourceAccessor;
    public void setMessageSource(MessageSource messageSource) { getInstance().messageSourceAccessor = new MessageSourceAccessor(messageSource);}
    public MessageSourceAccessor getMessageSource() { return messageSourceAccessor; }
    // -----------------------------------------------------------------------------------------------------
    
    
    
}
