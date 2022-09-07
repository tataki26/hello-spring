package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Component // or 스프링 빈에 직접 등록
@Aspect // AOP 사용시 필수
public class TimeTraceAop {

    // 원하는 곳 타겟팅
    @Around("execution(* hello.hellospring..*(..))") // 패키지 하위에 모두 적용
    // throws: 예외
    public Object execute(ProceedingJoinPoint jointPoint) throws Throwable{

        long start = System.currentTimeMillis();
        // 호출한 메서드의 이름 반환
        System.out.println("START: "+jointPoint.toString());
        try{
            // 다음 메서드 진행
            return jointPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: "+jointPoint.toString()+" "+timeMs+"ms");
        }
    }
}
