module kriolos.ewallet.mpesa {

    // Required 
    requires java.net.http; 
    requires java.logging; 
    
    // 🟢 THE CORRECT WAY: Required ONLY at compile time, optional at runtime
    requires static com.google.auto.service;
    
    
    // 🟢 FIX: Opens the package to the JUnit framework for reflection-based test execution
    opens io.github.kriolos.pos.plugin.fiscal.saft to org.junit.jupiter.api;

}
