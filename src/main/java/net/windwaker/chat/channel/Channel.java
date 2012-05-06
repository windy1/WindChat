/* Copyright (c) 2012 Walker Crouse, http://windwaker.net/
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.windwaker.chat.channel;

import net.windwaker.chat.ChatLogger;

import java.util.HashSet;
import java.util.Set;

import static java.util.regex.Matcher.quoteReplacement;

public class Channel {
	private final String name;
	private final Set<Chatter> chatters = new HashSet<Chatter>();
	private final ChatLogger logger = ChatLogger.getInstance();
	private String format;

	public Channel(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public boolean addChatter(Chatter chatter) {
		return chatters.add(chatter);
	}
	
	public void broadcast(String message) {
		String formattedMessage = format(format, name, message);
		for (Chatter chatter : chatters) {
			chatter.send(formattedMessage);
		}

		logger.info(formattedMessage);
	}
	
	public static String format(String format, String name, String message) {
		format = format.replaceAll("%channel%", quoteReplacement("%1$s")).replaceAll("%message%", quoteReplacement("%2$s")).replaceAll("&", "§");
		try {
			return String.format(format, name, message);
		} catch (Throwable t) {
			return null;
		}
	}
}
