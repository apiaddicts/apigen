package net.cloudappi.apigen.archetypecore.interceptors.expand;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
public class ExpandPathInterceptor extends ExpandInterceptor {

    private int maxLevel;
    private Set<String> allowed;
    private Set<String> excluded;

    public ExpandPathInterceptor(Integer maxLevel, Set<String> allowed, Set<String> excluded) {
        this.maxLevel = maxLevel;
        this.allowed = allowed;
        this.excluded = excluded;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ApigenExpand expandAnnotation = method.getAnnotation(ApigenExpand.class);

            if (expandAnnotation != null) return true;
        }

        validate(request, allowed, excluded, maxLevel);
        return true;
    }
}