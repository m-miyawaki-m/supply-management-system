package com.example.supply.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * APIリクエスト/レスポンスのロギングインターセプター
 *
 * <p>すべてのAPIエンドポイントに対して、リクエスト受信時とレスポンス返却時に
 * ログを出力します。処理時間の測定も行います。</p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-23
 */
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final String START_TIME_ATTRIBUTE = "startTime";

    /**
     * リクエスト処理前の処理
     *
     * <p>リクエストのメソッド、URI、クエリパラメータをログ出力し、
     * 処理時間計測のために開始時刻を記録します。</p>
     *
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param handler ハンドラー
     * @return 処理を続行する場合はtrue
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME_ATTRIBUTE, startTime);

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();

        if (queryString != null) {
            log.info(">>> Request: {} {} ? {}", method, uri, queryString);
        } else {
            log.info(">>> Request: {} {}", method, uri);
        }

        return true;
    }

    /**
     * リクエスト処理後の処理（正常終了時・例外発生時共通）
     *
     * <p>レスポンスのステータスコードと処理時間をログ出力します。</p>
     *
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param handler ハンドラー
     * @param ex 発生した例外（正常終了時はnull）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);
        if (startTime == null) {
            return;
        }

        long duration = System.currentTimeMillis() - startTime;
        int status = response.getStatus();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        if (ex != null) {
            log.error("<<< Response: {} {} - Status: {} - {}ms - Error: {}",
                    method, uri, status, duration, ex.getMessage());
        } else if (status >= 400) {
            log.warn("<<< Response: {} {} - Status: {} - {}ms", method, uri, status, duration);
        } else {
            log.info("<<< Response: {} {} - Status: {} - {}ms", method, uri, status, duration);
        }
    }
}
