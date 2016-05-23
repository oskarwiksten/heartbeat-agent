/**
 * The MIT License
 * Copyright (c) 2016 Avanza Bank AB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.avanza.heartbeat.agent;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.URL;
import java.util.Optional;
import java.util.Properties;

import org.junit.Test;

public class HeartbeatPropertiesResolverTest {

	@Test
	public void resolveProperties() throws Exception {
		Properties p = new Properties();
		p.setProperty("heartbeat.agent.application.name", "my-app");
		p.setProperty("heartbeat.agent.url", "http://my.heartbeat.server/beat");
		p.setProperty("heartbeat.agent.application.version", "1.0");
		System.setProperty("com.sun.management.jmxremote.port", "123");
		HeartbeatPropertiesResolver resolver = new HeartbeatPropertiesResolver(() -> p);
		HeartbeatProperties props = resolver.resolveProperties();
		assertThat(props.getApplicationName(), equalTo("my-app"));
		assertThat(props.getUrl(), equalTo(new URL("http://my.heartbeat.server/beat")));
		assertThat(props.getVersion(), equalTo("1.0"));
		assertThat(props.getPid(), greaterThan(0));
		assertThat(props.getJmxPort(), not(equalTo(Optional.empty())));
	}

}
