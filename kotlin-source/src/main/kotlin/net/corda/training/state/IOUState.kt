package net.corda.training.state

import net.corda.core.contracts.Amount
import net.corda.core.contracts.ContractState
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import net.corda.finance.DOLLARS
import net.corda.finance.POUNDS
import java.util.*

/**
 * This is where you'll add the definition of your state object. Look at the unit tests in [IOUStateTests] for
 * instructions on how to complete the [IOUState] class.
 *
 * Remove the "val data: String = "data" property before starting the [IOUState] tasks.
 */

/**
 * The IOU State object, with the following properties:
 * - [amount] The amount owed by the [borrower] to the [lender]
 * - [lender] The lending party.
 * - [borrower] The borrowing party.
 * - [contract] Holds a reference to the [IOUContract]
 * - [paid] Records how much of the [amount] has been paid.
 * - [linearId] A unique id shared by all LinearState states representing the same agreement throughout history within
 *   the vaults of all parties. Verify methods should check that one input and one output share the id in a transaction,
 *   except at issuance/termination.
 */
data class IOUState(
        val amount: Amount<Currency>,
        val lender: Party,
        val borrower: Party,
        val paid: Amount<Currency> = Amount(0, amount.token),
        override val linearId : UniqueIdentifier = UniqueIdentifier()
): LinearState {
    override val participants: List<Party> get() = listOf(lender,borrower)

    //[pay] adds an amount to the paid property. It does no validation.
    fun pay(amountToPay: Amount<Currency>) = copy(paid = paid.plus(amountToPay))
    //[withNewLender] creates a copy of the current state with a newly specified lender. For use when transferring.
    fun withNewLender(newLender: Party) = copy(lender = newLender)
}
