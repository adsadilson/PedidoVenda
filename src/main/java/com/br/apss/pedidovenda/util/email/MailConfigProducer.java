package com.br.apss.pedidovenda.util.email;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.br.apss.pedidovenda.model.ConfigEmail;
import com.br.apss.pedidovenda.service.ConfigEmailService;
import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.SimpleMailConfig;

public class MailConfigProducer {

	@Inject
	private ConfigEmailService configEmailService;

	@Produces
	@ApplicationScoped
	public SessionConfig getMailConfig() throws IOException {

		ConfigEmail conf = configEmailService.emailEmUso();

		SimpleMailConfig config = new SimpleMailConfig();
		config.setServerHost(conf.getSmtp());
		config.setServerPort(conf.getPorta());
		config.setEnableSsl(conf.getSsl());
		config.setAuth(true);
		config.setUsername(conf.getLogin());
		config.setPassword(conf.getSenha());

		return config;
	}
}
