/*
 * Decompiled with CFR 0.152.
 */
package galaxyspace.core.hooklib.asm;

public enum ReturnCondition {
    NEVER(false),
    ALWAYS(false),
    ON_TRUE(true),
    ON_NULL(true),
    ON_NOT_NULL(true);

    public final boolean requiresCondition;

    private ReturnCondition(boolean requiresCondition) {
        this.requiresCondition = requiresCondition;
    }
}

