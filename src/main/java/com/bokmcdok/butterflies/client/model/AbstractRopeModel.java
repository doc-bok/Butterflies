package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.world.entity.decoration.AbstractRopeEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractRopeModel<T extends AbstractRopeEntity> extends HierarchicalModel<T> {

    protected static final String LOWER = "lower";

    protected static final float ROPE_CONNECT_ANGLE = 45 * ((float)Math.PI / 180F);

    private final ModelPart root;
    protected final ModelPart lower;

    public AbstractRopeModel(ModelPart modelPart) {
        this.root = modelPart;
        this.lower = modelPart.getChild(LOWER);
    }

    @NotNull
    @Override
    public ModelPart root() {
        return this.root;
    }
}
