package cd.zgeniuscoders.zwallet.modules.profile.presentation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage() {
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showChangePhotoDialog by remember { mutableStateOf(false) }
    var showChangeUsernameDialog by remember { mutableStateOf(false) }
    var showChangeCurrencyDialog by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var selectedCurrency by remember { mutableStateOf("EUR (€)") }
    var userName by remember { mutableStateOf("John Doe") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Profil",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            // Carte profil utilisateur
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(60.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "John Doe",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "john.doe@email.com",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Membre depuis janvier 2024",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = { showEditProfileDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Modifier le profil"
                        )
                    }
                }
            }
        }

        item {
            // Statistiques rapides
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Statistiques du mois",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem("Dépenses", "2,450 €", Color(0xFFE57373))
                        StatItem("Recettes", "3,200 €", Color(0xFF81C784))
                        StatItem("Économies", "750 €", Color(0xFF64B5F6))
                    }
                }
            }
        }

        item {
            Text(
                text = "Paramètres",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    SettingsItem(
                        icon = Icons.Default.Notifications,
                        title = "Notifications",
                        subtitle = "Gérer les notifications",
                        trailing = {
                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = { notificationsEnabled = it }
                            )
                        }
                    )

                    Divider()

                    SettingsItem(
                        icon = Icons.Default.DarkMode,
                        title = "Mode sombre",
                        subtitle = "Activer le thème sombre",
                        trailing = {
                            Switch(
                                checked = isDarkMode,
                                onCheckedChange = { isDarkMode = it }
                            )
                        }
                    )

                    Divider()

                    SettingsItem(
                        icon = Icons.Default.Language,
                        title = "Langue",
                        subtitle = "Français",
                        onClick = { /* Changer la langue */ }
                    )

                    Divider()

                    SettingsItem(
                        icon = Icons.Default.CreditCard,
                        title = "Devises",
                        subtitle = "EUR (€)",
                        onClick = { /* Changer la devise */ }
                    )
                }
            }
        }

        item {
            Text(
                text = "Sécurité",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    SettingsItem(
                        icon = Icons.Default.Lock,
                        title = "Changer le mot de passe",
                        subtitle = "Modifier votre mot de passe",
                        onClick = { showChangePasswordDialog = true }
                    )

                    Divider()

                    SettingsItem(
                        icon = Icons.Default.AccountCircle,
                        title = "Modifier la photo de profil",
                        subtitle = "Changer votre avatar",
                        onClick = { showChangePhotoDialog = true }
                    )

                    Divider()

                    SettingsItem(
                        icon = Icons.Default.Edit,
                        title = "Modifier le nom d'utilisateur",
                        subtitle = userName,
                        onClick = { showChangeUsernameDialog = true }
                    )

                    Divider()

                    SettingsItem(
                        icon = Icons.Default.CreditCard,
                        title = "Modifier la devise",
                        subtitle = selectedCurrency,
                        onClick = { showChangeCurrencyDialog = true }
                    )

                    Divider()

                    SettingsItem(
                        icon = Icons.Default.Security,
                        title = "Authentification à deux facteurs",
                        subtitle = "Sécuriser votre compte",
                        onClick = { /* Configurer 2FA */ }
                    )
                }
            }
        }

        item {
            Text(
                text = "Support",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    SettingsItem(
                        icon = Icons.Default.Help,
                        title = "Centre d'aide",
                        subtitle = "FAQ et support",
                        onClick = { /* Ouvrir l'aide */ }
                    )

                    Divider()

                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "À propos",
                        subtitle = "Version 1.0.0",
                        onClick = { /* Afficher les infos */ }
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
            ) {
                SettingsItem(
                    icon = Icons.Default.ExitToApp,
                    title = "Se déconnecter",
                    subtitle = "Quitter l'application",
                    iconTint = Color(0xFFD32F2F),
                    titleColor = Color(0xFFD32F2F),
                    onClick = { showLogoutDialog = true }
                )
            }
        }
    }

    if (showEditProfileDialog) {
        EditProfileDialog(
            onDismiss = { showEditProfileDialog = false },
            onConfirm = {
                showEditProfileDialog = false
            }
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Se déconnecter") },
            text = { Text("Êtes-vous sûr de vouloir vous déconnecter ?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Logique de déconnexion
                        showLogoutDialog = false
                    }
                ) {
                    Text("Se déconnecter", color = Color(0xFFD32F2F))
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Annuler")
                }
            }
        )
    }

    if (showChangePasswordDialog) {
        ChangePasswordDialog(
            onDismiss = { showChangePasswordDialog = false },
            onConfirm = {
                showChangePasswordDialog = false
            }
        )
    }

    if (showChangePhotoDialog) {
        ChangePhotoDialog(
            onDismiss = { showChangePhotoDialog = false },
            onConfirm = {
                showChangePhotoDialog = false
            }
        )
    }

    if (showChangeUsernameDialog) {
        ChangeUsernameDialog(
            currentUsername = userName,
            onDismiss = { showChangeUsernameDialog = false },
            onConfirm = { newUsername ->
                userName = newUsername
                showChangeUsernameDialog = false
            }
        )
    }

    if (showChangeCurrencyDialog) {
        ChangeCurrencyDialog(
            currentCurrency = selectedCurrency,
            onDismiss = { showChangeCurrencyDialog = false },
            onConfirm = { newCurrency ->
                selectedCurrency = newCurrency
                showChangeCurrencyDialog = false
            }
        )
    }
}

@Composable
fun StatItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    titleColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = titleColor
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (trailing != null) {
            trailing()
        } else if (onClick != null) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun EditProfileDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var name by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("john.doe@email.com") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Modifier le profil") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom complet") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Sauvegarder")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}

@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var currentPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Changer le mot de passe") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    label = { Text("Mot de passe actuel") },
                    visualTransformation = if (currentPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { currentPasswordVisible = !currentPasswordVisible }) {
                            Icon(
                                if (currentPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("Nouveau mot de passe") },
                    visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                            Icon(
                                if (newPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmer le nouveau mot de passe") },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                if (confirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    isError = confirmPassword.isNotEmpty() && newPassword != confirmPassword,
                    modifier = Modifier.fillMaxWidth()
                )

                if (confirmPassword.isNotEmpty() && newPassword != confirmPassword) {
                    Text(
                        text = "Les mots de passe ne correspondent pas",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = currentPassword.isNotBlank() && newPassword.isNotBlank() &&
                        newPassword == confirmPassword && newPassword.length >= 6
            ) {
                Text("Changer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}

@Composable
fun ChangePhotoDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Modifier la photo de profil") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Choisissez une option pour modifier votre photo de profil",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { /* Ouvrir la caméra */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null)
                            Text("Caméra")
                        }
                    }

                    OutlinedButton(
                        onClick = { /* Ouvrir la galerie */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                            Text("Galerie")
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Fermer")
            }
        }
    )
}

@Composable
fun ChangeUsernameDialog(
    currentUsername: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var newUsername by remember { mutableStateOf(currentUsername) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Modifier le nom d'utilisateur") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Entrez votre nouveau nom d'utilisateur",
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = newUsername,
                    onValueChange = { newUsername = it },
                    label = { Text("Nom d'utilisateur") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(newUsername) },
                enabled = newUsername.isNotBlank() && newUsername != currentUsername
            ) {
                Text("Modifier")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}

@Composable
fun ChangeCurrencyDialog(
    currentCurrency: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val currencies = listOf(
        "EUR (€)" to "Euro",
        "USD ($)" to "Dollar américain",
        "GBP (£)" to "Livre sterling",
        "JPY (¥)" to "Yen japonais",
        "CHF (CHF)" to "Franc suisse",
        "CAD ($)" to "Dollar canadien",
        "AUD ($)" to "Dollar australien",
        "CNY (¥)" to "Yuan chinois",
        "XOF (CFA)" to "Franc CFA",
        "MAD (DH)" to "Dirham marocain"
    )

    var selectedCurrency by remember { mutableStateOf(currentCurrency) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Choisir la devise") },
        text = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(currencies) { (code, name) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedCurrency = code }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCurrency == code,
                            onClick = { selectedCurrency = code }
                        )
                        Column(
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = code,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = name,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(selectedCurrency) }
            ) {
                Text("Confirmer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}
