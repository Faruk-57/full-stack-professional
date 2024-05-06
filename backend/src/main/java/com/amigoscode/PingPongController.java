
@RestController
public class PingPongController {

    record PingPong(String result){}

    @GetMapping("/ping")
    public PingPong getPinPong(){
        return new PingPong("PONG");

}
}