package builder.player;

import builder.inventory.items.Item;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.Sprite;
import engine.art.sprites.SpriteGroup;
import engine.game.Direction;
import engine.game.Entity;
import engine.timing.Animation;
import engine.timing.AnimationDuration;

public class ChickenFarmer extends Entity implements Player {
    private final SpriteGroup art = SpriteGallery.chickenFarmer;
    private final Animation leftAnimation;
    private final Animation rightAnimation;
    private Animation useAnimation;

    public ChickenFarmer(int x, int y) {
        super(x, y);

        // Initialize animations
        this.leftAnimation = new Animation(
                AnimationDuration.SLOW,
                new Sprite[] {
                        art.getSprite("left"),
                        art.getSprite("left1"),
                        art.getSprite("left2")
                }
        );

        this.rightAnimation = new Animation(
                AnimationDuration.SLOW,
                new Sprite[] {
                        art.getSprite("right"),
                        art.getSprite("right1"),
                        art.getSprite("right2")
                }
        );

        // Set initial sprite
        setSprite(art.getSprite("down"));
    }

    @Override
    public int getDamage() {
        return 2;
    }

    public void move(Direction direction, int amount) {
        switch (direction) {
            case NORTH:
                setY(getY() - amount);
                setSprite(art.getSprite("up"));
                break;
            case SOUTH:
                setY(getY() + amount);
                setSprite(art.getSprite("down"));
                break;
            case EAST:
                setX(getX() + amount);
                setSprite(rightAnimation);
                break;
            case WEST:
                setX(getX() - amount);
                setSprite(leftAnimation);
                break;
        }
    }

    @Override
    public void tick(EngineState state) {
        setSprite(art.getSprite("down"));

        leftAnimation.tick(state);
        rightAnimation.tick(state);
        if (useAnimation != null) {
            useAnimation.tick(state);
        }
    }

    public void use(Item item) {
        if (item == null) {
            return;
        }

        if (item.useAnimation().isPresent()) {
            this.useAnimation = item.useAnimation().get();
            setSprite(useAnimation);
        }
    }
}