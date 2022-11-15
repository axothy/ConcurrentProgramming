package ch3;

import java.util.HashSet;
import java.util.Set;

//Immutable
public final class ThreeStooges {
    private final Set<String> stooges = new HashSet<String>();

    public ThreeStooges() {
        stooges.add("Moe");
        stooges.add("Sex");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }
}
