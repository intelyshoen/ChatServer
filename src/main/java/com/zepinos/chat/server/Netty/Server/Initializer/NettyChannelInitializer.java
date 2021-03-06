package com.zepinos.chat.server.Netty.Server.Initializer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("nettyChannelInitializer")
public class NettyChannelInitializer extends ChannelInitializer<Channel> {

	private static final StringDecoder STRING_DECODER = new StringDecoder(CharsetUtil.UTF_8);
	private static final StringEncoder STRING_ENCODER = new StringEncoder(CharsetUtil.UTF_8);

	@Value("${netty.server.log.level.pipeline}")
	private String logLevelPipeline;

	@Autowired
	private ChannelInboundHandlerAdapter jsonHandler;

	@Override
	protected void initChannel(Channel channel) throws Exception {

		channel.pipeline()
				.addLast(new LineBasedFrameDecoder(Integer.MAX_VALUE))
				.addLast(STRING_DECODER)
				.addLast(STRING_ENCODER)
				.addLast(new LoggingHandler(LogLevel.valueOf(logLevelPipeline)))
				.addLast(jsonHandler);

	}

}
