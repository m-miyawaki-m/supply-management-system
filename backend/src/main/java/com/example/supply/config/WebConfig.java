package com.example.supply.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC設定クラス
 *
 * <p>インターセプターなどのWebアプリケーション設定を行います。</p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-23
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;

    /**
     * コンストラクタ
     *
     * @param loggingInterceptor ロギングインターセプター（自動インジェクション）
     */
    public WebConfig(LoggingInterceptor loggingInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
    }

    /**
     * インターセプターを登録します。
     *
     * <p>ロギングインターセプターを/api配下の全エンドポイントに適用します。</p>
     *
     * @param registry インターセプターレジストリ
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/api/**");
    }
}
