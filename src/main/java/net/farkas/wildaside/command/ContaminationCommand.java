package net.farkas.wildaside.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.farkas.wildaside.util.ContaminationHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

public class ContaminationCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("contamination")
                .then(Commands.argument("action", StringArgumentType.word())
                        .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(new String[]{"add", "get", "set", "clear"}, builder))
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(ctx -> {
                                            String action = StringArgumentType.getString(ctx, "action");
                                            Collection<? extends Entity> targets = EntityArgument.getEntities(ctx, "targets");
                                            int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                            return execute(ctx.getSource(), action, targets, amount);
                                        })
                                )
                                .executes(ctx -> {
                                    String action = StringArgumentType.getString(ctx, "action");
                                    Collection<? extends Entity> targets = EntityArgument.getEntities(ctx, "targets");
                                    return execute(ctx.getSource(), action, targets, 0);
                                })
                        )
                )
        );
    }

    private static int execute(CommandSourceStack source, String action, Collection<? extends Entity> targets, int amount) {
        int affected = 0;
        for (Entity entity : targets) {
            if (!(entity instanceof LivingEntity living)) continue;

            switch (action.toLowerCase()) {
                case "add" -> {
                    ContaminationHandler.addDose(living, amount);
                    source.sendSuccess(() -> Component.literal("Added " + amount + " of contamination to " + living.getName().getString()), false);
                    affected++;
                }
                case "get" -> {
                    int current = ContaminationHandler.getDose(living);
                    source.sendSuccess(() -> Component.literal(living.getName().getString() + " has " + current + " contamination"), false);
                    affected++;
                }
                case "set" -> {
                    ContaminationHandler.setDose(living, amount);
                    source.sendSuccess(() -> Component.literal("Set contamination of " + living.getName().getString() + " to " + amount), false);
                    affected++;
                }
                case "clear" -> {
                    ContaminationHandler.setDose(living ,0);
                    source.sendSuccess(() -> Component.literal("Cleared contamination of " + living.getName().getString()), false);
                    affected++;
                }
                default -> {
                    source.sendFailure(Component.literal("Invalid action: " + action));
                    return 0;
                }
            }
        }

        if (affected < 0) {
            source.sendFailure(Component.literal("No valid living entities found."));
        }
        return affected;
    }
}